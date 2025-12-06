package leipflix;

import java.util.List;

public interface IContentRepository {
    void addContent(Streamable content);
    List<Streamable> getContent();
}
