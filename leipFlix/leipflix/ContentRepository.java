package leipflix;

import java.util.ArrayList;
import java.util.List;

public class ContentRepository implements IContentRepository {
    private List<Streamable> content;

    public ContentRepository() {
        content = new ArrayList<Streamable>();
    }

    @Override
    public void addContent(Streamable item) {
        content.add(item);
    }

    @Override
    public List<Streamable> getContent() {
        return content;
    }
}
