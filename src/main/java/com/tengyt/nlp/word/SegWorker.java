package com.tengyt.nlp.word;

import com.google.common.collect.Queues;
import org.ansj.domain.Term;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

/**
 * Created by tengyt on 8/25/15.
 */
public class SegWorker implements Callable<List<Term>>{
    public static Queue<Email> emails = Queues.newConcurrentLinkedQueue();

    @Override
    public List<Term> call() throws Exception {
        while (true) {
            if (emails.isEmpty()) {
                Thread.sleep(1000);
                continue;
            }

            Email email = emails.poll();
        }
        return null;
    }
}
