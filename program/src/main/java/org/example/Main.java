package org.example;

import org.example.entity.AnalysisResponse;
import org.example.util.ApiUtil;
import org.example.util.Localization;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class Main {
    static {
        Localization.init(Locale.getDefault());
    }

    private static final String EXIT = Localization.get("info.exit");

    public static void main(String[] args) throws Exception {
        if (ApiUtil.API_KEY == null) {
            System.out.println(Localization.get("exception.no_api_key"));
            System.out.println(EXIT);
            System.in.read();
            return;
        }

        if (args.length == 0) {
            System.out.println(Localization.get("exception.no_file_path"));
            System.out.println(EXIT);
            System.in.read();
            return;
        }

        if (!Files.exists(Path.of(args[0]))) {
            System.out.println(Localization.get("exception.no_specified_file"));
            System.out.println(EXIT);
            System.in.read();
            return;
        }

        AnalysisResponse analysisResponse = ApiUtil.processFile(args);
        if (analysisResponse != null) ApiUtil.printScanResults(analysisResponse);

        System.out.println(EXIT);
        System.in.read();
    }
}
