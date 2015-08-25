package com.tengyt.nlp.word;

import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

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

    private int idealCount = 0;

    @Override
    public void run() {
        while (idealCount < 200) {
            if (emails.isEmpty()) {
                idealCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            Email email = emails.poll();
            for (Term term : NlpAnalysis.parse(email.getContent())) {
                terms.add(term.toString());
            }
        }
    }
}
