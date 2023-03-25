import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

public class SentenceRelationshipDetector {
  private StanfordCoreNLP pipeline;

  public SentenceRelationshipDetector() {
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
    pipeline = new StanfordCoreNLP(props);
  }

  public String detectRelationship(String sentence1, String sentence2) {
    Annotation document1 = new Annotation(sentence1);
    pipeline.annotate(document1);
    CoreMap sentence1Map = document1.get(CoreAnnotations.SentencesAnnotation.class).get(0);

    Annotation document2 = new Annotation(sentence2);
    pipeline.annotate(document2);
    CoreMap sentence2Map = document2.get(CoreAnnotations.SentencesAnnotation.class).get(0);

    int sentiment1 = (int)sentence1Map.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class).score();
    int sentiment2 = (int)sentence2Map.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class).score();

    SemanticGraph dependencies1 = sentence1Map.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class);
    SemanticGraph dependencies2 = sentence2Map.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class);
    double similarity = dependencies1.equals(dependencies2) ? 1.0 : 0.0;

    int sentimentDiff = Math.abs(sentiment1 - sentiment2);

    if (similarity == 1.0 && sentimentDiff <= 1) {
      return "The two sentences are semantically equivalent";
    } else if (sentiment1 > sentiment2) {
      return "The first sentence is more positive";
    } else if (sentiment1 < sentiment2) {
      return "The second sentence is more positive";
    } else {
      return "The two sentences are unrelated";
    }
  }
}
