package main.auditoriumupdateservice.services;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

@Service
public class SolrService {

    private final SolrClient solrClient;

    public SolrService(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public void addDocument(SolrInputDocument doc) throws Exception {
        solrClient.add(doc);
        solrClient.commit();
    }

    public void close() throws Exception {
        solrClient.close();
    }
}