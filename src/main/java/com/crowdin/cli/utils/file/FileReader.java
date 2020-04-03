package com.crowdin.cli.utils.file;

import com.crowdin.cli.utils.MessageSource;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.ResourceBundle;

import static com.crowdin.cli.utils.console.ExecutionStatus.WARNING;


public class FileReader {

    private static final ResourceBundle RESOURCE_BUNDLE = MessageSource.RESOURCE_BUNDLE;

    private static final String YAML_EXTENSION = ".yaml";

    private static final String YML_EXTENSION = ".yml";

    public Map<String, Object> readCliConfig(File fileCfg) {
        if (fileCfg == null) {
            throw new NullPointerException("FileReader.readCliConfig has null args");
        }
        if (!(fileCfg.getName().endsWith(YAML_EXTENSION) || fileCfg.getName().endsWith(YML_EXTENSION))) {
            System.out.println(WARNING.withIcon(String.format(RESOURCE_BUNDLE.getString("message.warning.not_yml"), fileCfg.getAbsolutePath())));
        }

        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(fileCfg)){
            return (Map<String, Object>) yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(RESOURCE_BUNDLE.getString("error.configuration_file_not_exist"));
        } catch (Exception e) {
            throw new RuntimeException(RESOURCE_BUNDLE.getString("error.reading_configuration_file"), e);
        }
    }
}