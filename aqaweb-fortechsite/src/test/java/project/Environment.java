package project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Environment {

    private final static Logger logger = Logger.getLogger(Environment.class.getName());

    private static final String SCOPE_DELIMITER = ".";

    private static final Pattern pattern = Pattern.compile("\\$\\{(.+)}");

    private Map<String, String> context;

    private Environment(Map<String, String> context) {
        this.context = context;
    }

    public static Environment newInstance(String contextFilePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(
                    Environment.class.getClassLoader().getResourceAsStream(contextFilePath));
            Map<String, String> context = new HashMap<>();
            if (rootNode != null) {
                readContext(context, rootNode);
            }
            flattenContext(context);
            return new Environment(context);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred when reading the context", e);
            return new Environment(new HashMap<>());
        }
    }

    private static void readContext(Map<String, String> context, JsonNode node) {
        readContext(context, node, "");
    }

    private static void readContext(Map<String, String> context, JsonNode node, String path) {
        if (node.isValueNode()) {
            context.put(path, node.asText());
        } else if (node.isObject()) {
            for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> entry = it.next();
                readContext(context, entry.getValue(), joinPaths(path, entry.getKey()));
            }
        }
    }

    private static String joinPaths(String a, String b) {
        return a == null || a.trim().isEmpty() ? b : String.join(SCOPE_DELIMITER, a, b);
    }

    private static void flattenContext(Map<String, String> context) {
        boolean referencesResolved = true;
        while (referencesResolved) {
            referencesResolved = false;
            for (Map.Entry<String, String> entry : context.entrySet()) {
                String val = entry.getValue();
                Matcher m = pattern.matcher(entry.getValue());
                while (m.find()) {
                    String value = context.getOrDefault(m.group(1), m.group(1));
                    val = val.replace(m.group(0), value);
                    m = pattern.matcher(val);
                    referencesResolved = true;
                }
                context.put(entry.getKey(), val);
            }
        }
    }

    public String resolve(String key) {
        return context.getOrDefault(key, key);
    }
}
