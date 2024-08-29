package main.auditoriumupdateservice.services;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;

@Service
public class SolrService implements Closeable {

    private final SolrClient solrClient;

    public SolrService(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public void addDocument(SolrInputDocument doc) throws Exception {
        solrClient.add(doc);
        solrClient.commit();
    }

    @Override
    public void close() throws IOException {
        solrClient.close();
    }
}
