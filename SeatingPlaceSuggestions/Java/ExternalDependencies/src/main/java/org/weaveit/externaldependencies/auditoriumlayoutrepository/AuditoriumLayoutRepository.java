package org.weaveit.externaldependencies.auditoriumlayoutrepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuditoriumLayoutRepository {

    private final Map<String, AuditoriumDto> repository = new HashMap<>();

    public AuditoriumLayoutRepository() throws IOException {
        String jsonDirectory = Paths.get(System.getProperty("user.dir")).getParent().getParent().getParent().toString() + "/Stubs/AuditoriumLayouts";

        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(jsonDirectory));

        for (Path path : directoryStream) {
            if (path.toString().contains("_theater.json")) {
                String fileName = path.getFileName().toString();
                ObjectMapper mapper = new ObjectMapper();
                repository.put(fileName.split("-")[0], mapper.readValue(path.toFile(), AuditoriumDto.class));
            }
        }
    }

    public AuditoriumDto getAuditoriumLayoutFor(String showId) {
        if (repository.containsKey(showId)) {
            return repository.get(showId);
        }
        return new AuditoriumDto(Collections.emptyMap(), Collections.emptyList());
    }

    public AuditoriumDto findByShowId(String showId) {
        if (repository.containsKey(showId))
        {
            return repository.get(showId);
        }

        return new AuditoriumDto(Collections.emptyMap(), Collections.emptyList());
    }
}
