package util;

import dto.MatchConfig;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHelper {
    final String configFile = "config.json";
    //JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();

    public MatchConfig readConfig() {
        MatchConfig config = new MatchConfig();

        try (FileReader reader = new FileReader(configFile)) {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            config = parseConfigObject(obj);
        } catch (IOException | ParseException fileNotFoundException) {
            // nếu file không hợp lệ thì lấy giá trị default
        }

        return config;
    }

    @SuppressWarnings("unchecked")
    public void saveConfig(MatchConfig matchConfig) {
        try (FileWriter fileWriter = new FileWriter(configFile)) {
            JSONObject obj = new JSONObject();

            obj.put("numberQty", matchConfig.getNumberQty());
            obj.put("time", matchConfig.getTime());
            obj.put("maxPlayer", matchConfig.getMaxPlayer());

            fileWriter.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param config JSONObject read from config.json
     * @return MatchConfig instance
     */
    private MatchConfig parseConfigObject(JSONObject config) {
        MatchConfig matchConfig = new MatchConfig();

        // nếu file không đúng format sẽ lấy giá trị default
        try {
            //Get number quantity
            int numberQty = ((Long) config.get("numberQty")).intValue();

            //Get time
            int time = ((Long) config.get("time")).intValue();

            //Get max player
            int maxPlayer = ((Long) config.get("maxPlayer")).intValue();

            matchConfig.setNumberQty(numberQty);
            matchConfig.setTime(time);
            matchConfig.setMaxPlayer(maxPlayer);
        } catch (ClassCastException e) {
//            e.printStackTrace();
        }

        return matchConfig;
    }
}
