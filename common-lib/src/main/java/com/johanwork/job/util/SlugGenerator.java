package com.johanwork.job.util;

import java.util.function.Predicate;

public final class SlugGenerator {

    private SlugGenerator() {}

    public static String generate(String name, Predicate<String> existsBySlug) {
        String base = normalize(name);

        if (!existsBySlug.test(base)) {
            return base;
        }

        int counter = 1;
        while (existsBySlug.test(base + "-" + counter)) {
            counter++;
        }
        return base + "-" + counter;
    }

    private static String normalize(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]", "-");
    }
}
