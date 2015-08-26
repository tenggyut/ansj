package com.tengyt.nlp.word;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.StandardSystemProperty;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SegExecutor {
    private static ExecutorService workers = Executors.newFixedThreadPool(10);

    private File corpus;

    public SegExecutor(String corpus) {
        this.corpus= new File(corpus);
        Preconditions.checkArgument(this.corpus.exists()
                , "file not found : " + corpus);
    }

    public void doWork() throws IOException, InterruptedException {
        initWorker();
        List<String> oneEmail = Lists.newLinkedList();
        for (String line : Files.readLines(corpus, Charsets.UTF_8)) {
            if (line.startsWith("Received:")) {
                if (oneEmail.size() < 12) {
                    oneEmail.clear();
                    continue;
                }
                SegWorker.emails.add(new Email(oneEmail));
                oneEmail.clear();
            } else if (!Strings.isNullOrEmpty(line)) {
                    oneEmail.add(line);
                }
        }
        if (!oneEmail.isEmpty()) {
            SegWorker.emails.add(new Email(oneEmail));
        }

        while (!SegWorker.emails.isEmpty()) {
            System.out.println(".." + SegWorker.emails.size());
            Thread.sleep(2000);
        }
    }

    private void initWorker() {
        for (int i = 0; i < 10; i++) {
            workers.submit(new SegWorker());
        }
    }

    public static void main(String[] args) {
        SegExecutor executor = new SegExecutor("/Users/tengyt/IdeaProjects/ansj/src/main/resources/corpus.txt");
        try {
            executor.doWork();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("words: " + SegWorker.terms.size());
        try {
            export2File();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    private static void export2File() throws IOException {
        File out = new File("/Users/tengyt/Desktop/terms.txt");
        if (out.exists()) {
            out.delete();
        }
        out.createNewFile();
        int count = 0;
        for (String term : SegWorker.terms) {
            Files.append(term, out, Charsets.UTF_8);
            Files.append(StandardSystemProperty.LINE_SEPARATOR.value(), out, Charsets.UTF_8);
            count++;
            if (count % 1000 == 0) {
                System.out.println("export " + count);
            }
        }
    }
}
