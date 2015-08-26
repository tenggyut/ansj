package com.tengyt.nlp.word;


import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SegExecutorTest {

    @Test
    public void testDoWork() throws Exception {
        SegExecutor executor = new SegExecutor("/Users/tengyt/IdeaProjects/ansj/src/test/resources/corpus.txt");
        executor.doWork();
        Assert.assertTrue(SegWorker.emails.size() == 2);
        for (Term term : NlpAnalysis.parse(SegWorker.emails.poll().getContent())) {
            System.out.println(term.getName() + " " + term.getNatureStr());
        }
    }
}