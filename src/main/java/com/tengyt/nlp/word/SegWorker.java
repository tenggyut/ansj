package com.tengyt.nlp.word;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * seg worker
 *
 * Created by tengyt on 8/25/15.
 */
public class SegWorker implements Runnable {
    public static Queue<Email> emails = Queues.newConcurrentLinkedQueue();
    public static Set<String> terms = Sets.newConcurrentHashSet();

    private static List<String> skipNature = Lists.newArrayList(
            "w", "nr", "null","en", "m"
    );

    @Override
    public void run() {
        while (true) {
            if (emails.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            Email email = emails.poll();
            for (Term term : NlpAnalysis.parse(email.getContent())) {
                if (StringUtils.isEmpty(term.getName()) || isSkip(term.getNatureStr())) continue;
                terms.add(term.getName());
            }
        }
    }

    private boolean isSkip(String natureString) {
        return skipNature.contains(natureString);
    }
}
