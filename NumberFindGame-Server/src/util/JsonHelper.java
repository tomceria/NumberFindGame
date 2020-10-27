package util;

import dto.MatchConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonHelper {
    public MatchConfig readFile() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        String fileName = "config.json";
        MatchConfig config = null;

        try (FileReader reader = new FileReader(fileName)) {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            config = parseConfigObject(obj);
        } catch (IOException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        return config;
    }

    /**
     *
     * @param config JSONObject read from config.json
     * @return MathConfig instance
     */
    private MatchConfig parseConfigObject(JSONObject config)
    {
        MatchConfig matchConfig = new MatchConfig();

        //Get number quantity
        int numberQty = ((Long) config.get("numberQty")).intValue();

        //Get time
        int time = ((Long) config.get("time")).intValue();

        //Get max player
        int maxPlayer = ((Long) config.get("maxPlayer")).intValue();

        matchConfig.setNumberQty(numberQty);
        matchConfig.setTime(time);
        matchConfig.setMaxPlayer(maxPlayer);

        return matchConfig;
    }
}
