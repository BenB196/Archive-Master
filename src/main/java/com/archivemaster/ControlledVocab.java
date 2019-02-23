package com.archivemaster;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControlledVocab {

//------------------------------------------------------------
//
// Private methods
//
//------------------------------------------------------------

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url)
    {
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            is.close();

            return new JSONObject(jsonText);
        }
        catch (Exception e) {
            return new JSONObject();
        }
    }

    // Get values from Library of Congress JSON object
    private static String[] getLOCValues(JSONObject rawJson)
    {
        if(rawJson != null) {
            String[] values = new String[rawJson.length()];

            for(int i = 0; i < rawJson.length(); i++) {
                values[i] = rawJson.getString("value");
            }

            return values;
        }

        // Return empty string array if no json values are available
        return new String[0];
    }

//------------------------------------------------------------
//
// Public methods
//
//------------------------------------------------------------

    // Get controlled vocabularies for file type
    public static String[] GetFileTypeVocab() {
        String fileTypeUrl  = "http://id.loc.gov/vocabulary/mfiletype.json";

        JSONObject fileTypeJson = readJsonFromUrl(fileTypeUrl);
        String[] fileTypes = getLOCValues(fileTypeJson);

        return fileTypes;
    }

    // Get controlled vocabularies for geographic locations
    public static String[] GetGeographicVocab() {
        String countriesUrl = "http://id.loc.gov/vocabulary/countries.json";
        String geoAreasUrl = "http://id.loc.gov/vocabulary/geographicAreas.json";

        // TODO: parse json for values, merge lists, remove duplicates
        JSONObject countriesJson = readJsonFromUrl(countriesUrl);
        JSONObject geoAreasJson = readJsonFromUrl(geoAreasUrl);

        String[] countries = getLOCValues(countriesJson);
        String[] geoAreas = getLOCValues(geoAreasJson);

        // Combine LOC values
        List<String> list = new ArrayList(Arrays.asList(countries));
        list.addAll(Arrays.asList(geoAreas));
        String[] geographics = list.toArray(new String[0]);

        return geographics;
    }

    // Get controlled vocabularies for world languages
    public static String[] GetLanguageVocab() {
        String languageURL = "http://id.loc.gov/vocabulary/languages.json";

        JSONObject languageJson = readJsonFromUrl(languageURL);

        return getLOCValues(languageJson);
    }
}
