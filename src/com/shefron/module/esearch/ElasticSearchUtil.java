/**
 * 
 */
package com.shefron.module.esearch;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

public class ElasticSearchUtil {
    
    public static void addItem(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress("127.0.0.1".getBytes()), 9300));

            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                    .endObject();
            
            IndexResponse response = client.prepareIndex("cbosslog", "normal", "1")
                    .setSource(builder).get();
            
         // Index name
            String _index = response.getIndex();
            // Type name
            String _type = response.getType();
            // Document ID (generated or not)
            String _id = response.getId();
            // Version (if it's the first time you index this document, you will get: 1)
            long _version = response.getVersion();
            // isCreated() is true if the document is a new one, false if it has been updated
            boolean created = response.isCreated();
            
            System.out.println(_index +" "+_type+" "+_id+" "+_version+" "+created);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if(client != null){
                client.close();
            }
        }
    }
    
    public static void getItem(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

            GetResponse response = client.prepareGet("cbosslog", "normal", "1").setFetchSource(new String[]{"user","postDate"}, new String[]{"message"}).setOperationThreaded(false).get();
            if(response != null){
                System.out.println("#"+response);
                System.out.println(response.getId()+" "+response.getIndex()+ " "+response.getType()+"\n"+response.getSource());
                Map<String,Object> resultMap = response.getSource();
                System.out.println("#"+resultMap.get("postDate").getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if(client != null){
                client.close();
            }
        }
    }
    
    private static XContentBuilder jsonBuilder() throws Exception{
        return XContentFactory.jsonBuilder();
    }
    
    public static void bulkItems() {
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

            BulkRequestBuilder bulkRequest = client.prepareBulk();

            // either use client#prepare, or use Requests# to directly build
            // index/delete requests
            bulkRequest.add(client.prepareIndex("cbosslog", "normal", "2")
                    .setSource(jsonBuilder().startObject().field("user", "kimchy").field("postDate", new Date())
                            .field("message", "trying out Elasticsearch").endObject()));

            bulkRequest.add(client.prepareIndex("cbosslog", "normal", "3")
                    .setSource(jsonBuilder().startObject().field("user", "kimchy").field("postDate", new Date())
                            .field("message", "another post").endObject()));
            
            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                // process failures by iterating through each bulk response item
            }
            System.out.println("took:"+bulkResponse.getTookInMillis());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }

    }
    
    
    public static void bulkProcessor(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

            BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId, BulkRequest request) {

                }
                @Override
                public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

                }
                @Override
                public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                    System.out.println("#���ִ�����");
                }
            }).setBulkActions(10000).setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                    .setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(1)
                    .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();
            
            IndexRequest indexRequest = new IndexRequest("cbosslog", "normal", "4");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("user", "Shefron");
            map.put("postDate", new Date());
            map.put("message", "ҵ����Ϣshefron");
            indexRequest.source(map);
            
            bulkProcessor.add(indexRequest );
            
            
            bulkProcessor.awaitClose(5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }
    
    public static void searchItems(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

            SearchResponse response = client.prepareSearch("cbosslog")
                    .setTypes("normal")
//                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.matchQuery("message", "��Ϣ"))                 // Query
                    .setPostFilter(QueryBuilders.rangeQuery("postDate").from(new Date(System.currentTimeMillis()-1*24*60*60*1000)).to(new Date()))     // Filter
                    .setFrom(0).setSize(60).setExplain(true)
                    .addSort("postDate", SortOrder.DESC)
                    .setFetchSource("user", "")
                    .execute()
                    .actionGet();
            
            
            if(response != null){
                SearchHits hits = response.getHits();
                System.out.println("total:"+hits.getTotalHits());
                SearchHit[] hitArray = hits.getHits();
                for(SearchHit hit : hitArray){
                    System.out.println(hit.getId()+" -> "+hit.getSource());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }
    
    public static void scrollSearchItems(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            SearchResponse scrollResp = client.prepareSearch("cbosslog").setTypes("normal")
//                    .addSort(FieldSortBuilder.EMPTY_PARAMS, SortOrder.ASC)
                    .setScroll(new TimeValue(60000))
                    .setQuery(QueryBuilders.matchQuery("message", "��Ϣ"))
                    .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll
            //Scroll until no hits are returned
            while (true) {
                for (SearchHit hit : scrollResp.getHits().getHits()) {
                    System.out.println(hit.getId()+" -> "+hit.getSource());
                }
                scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
                //Break condition: No hits are returned
                if (scrollResp.getHits().getHits().length == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }
    
    public static void multiSearch(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            SearchRequestBuilder srb1 =
                client.prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);
            SearchRequestBuilder srb2 =
                client.prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

            MultiSearchResponse sr = client.prepareMultiSearch().add(srb1).add(srb2).execute().actionGet();

            // You will get all individual responses from
            // MultiSearchResponse#getResponses()
            long nbHits = 0;
            for (MultiSearchResponse.Item item : sr.getResponses()) {
                SearchResponse response = item.getResponse();
                nbHits += response.getHits().getTotalHits();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }
    
    public static void searchAggregation(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            
            SearchResponse sr = client.prepareSearch()
                    .setQuery(QueryBuilders.matchAllQuery())
                    .addAggregation(
                            AggregationBuilders.terms("agg1").field("field")
                    )
                    .addAggregation(
                            AggregationBuilders.dateHistogram("agg2")
                                    .field("birth")
                                    .interval(DateHistogramInterval.YEAR)
                    )
                    .execute().actionGet();

                // Get your facet results
                Terms agg1 = sr.getAggregations().get("agg1");
//                DateHistogramParser agg2 = sr.getAggregations().get("agg2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }
    
    public static void terminalAfter(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            
            SearchResponse sr = client.prepareSearch("cbosslog").setTerminateAfter(1000).get();
            if (sr.isTerminatedEarly()) {
                // We finished early
            }
            
//            SearchResponse sr = client.prepareSearch()
//                    .setQuery( /* your query */ )
//                    .addAggregation( /* add an aggregation */ )
//                    .execute().actionGet();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }
    
    public static void searchAdmin(){
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            
            AdminClient adminClient = client.admin();
            
            adminClient.indices().prepareCreate("cbosslog").get();
            
            adminClient.indices().prepareCreate("cbosslog")
            .setSettings(Settings.builder()             
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 2)
            )
            .get();
            
            adminClient.indices().prepareCreate("cbosslog")   
            .addMapping("error", "{\n" +                
                    "    \"error\": {\n" +
                    "      \"properties\": {\n" +
                    "        \"message\": {\n" +
                    "          \"type\": \"string\"\n" +
                    "        }\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }")
            .get();
            
            adminClient.indices().preparePutMapping("cbosslog")   
            .setType("error")                                
            .setSource("{\n" +                              
                    "  \"properties\": {\n" +
                    "    \"name\": {\n" +
                    "      \"type\": \"string\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}")
            .get();
            
         // You can also provide the type in the source document
            adminClient.indices().preparePutMapping("cbosslog")
                    .setType("user")
                    .setSource("{\n" +
                            "    \"user\":{\n" +                        
                            "        \"properties\": {\n" +
                            "            \"name\": {\n" +
                            "                \"type\": \"string\"\n" +
                            "            }\n" +
                            "        }\n" +
                            "    }\n" +
                            "}")
                    .get();
            
            adminClient.indices().preparePutMapping("cbosslog")   
            .setType("user")                               
            .setSource("{\n" +                              
                    "  \"properties\": {\n" +
                    "    \"user_name\": {\n" +
                    "      \"type\": \"string\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}")
            .get();
            
            adminClient.indices().prepareRefresh().get();
            adminClient.indices().prepareRefresh("cbosslog").get();
            adminClient.indices().prepareRefresh("cbosslog", "company").get();
            
            GetSettingsResponse response = adminClient.indices().prepareGetSettings("cbosslog", "employee").get();
            for (ObjectObjectCursor<String, Settings> cursor : response.getIndexToSettings()) {
                String index = cursor.key;
                Settings settings = cursor.value;
                Integer shards = settings.getAsInt("index.number_of_shards", null);
                Integer replicas = settings.getAsInt("index.number_of_replicas", null);
            }
            
            adminClient.indices().prepareUpdateSettings("cbosslog")
                    .setSettings(Settings.builder().put("index.number_of_replicas", 0)).get();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }
    
    public static void searchClusterAdmin(){
        
        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            AdminClient adminClient = client.admin();
            
            ClusterHealthResponse healths = adminClient.cluster().prepareHealth().get(); 
            String clusterName = healths.getClusterName();              
            int numberOfDataNodes = healths.getNumberOfDataNodes();     
            int numberOfNodes = healths.getNumberOfNodes();             

            for (ClusterIndexHealth health : healths.getIndices().values()) {                 
                String index = health.getIndex();                       
                int numberOfShards = health.getNumberOfShards();        
                int numberOfReplicas = health.getNumberOfReplicas();    
                ClusterHealthStatus status = health.getStatus();        
            }
            
            
            client.admin().cluster().prepareHealth().setWaitForYellowStatus().get();
            client.admin().cluster().prepareHealth("company").setWaitForGreenStatus().get();
            client.admin().cluster().prepareHealth("employee").setWaitForGreenStatus()
                    .setTimeout(TimeValue.timeValueSeconds(2)).get();
            
            
            ClusterHealthResponse response =
                client.admin().cluster().prepareHealth("company").setWaitForGreenStatus().get();

            ClusterHealthStatus status = response.getIndices().get("company").getStatus();
            if (!status.equals(ClusterHealthStatus.GREEN)) {
                throw new RuntimeException("Index is in " + status + " state");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // on shutdown
            if (client != null) {
                client.close();
            }
        }
    }

    public static void main(String[] args) {
//        addItem();
//        getItem();
//        bulkItems();
//        bulkProcessor();
//        searchItems();
        scrollSearchItems();
 
        
//        Settings settings = Settings.settingsBuilder().put("cluster.name", "myClusterName").build();
//        Client client2 = TransportClient.builder().settings(settings).build();
//        // Add transport addresses and do something with the client...
//
//        Settings settings2 = Settings.settingsBuilder().put("client.transport.sniff", true).build();
//        TransportClient client3 = TransportClient.builder().settings(settings).build();
        
        /**
         * 
         * Parameter Description client.transport.ignore_cluster_name
         * 
         * Set to true to ignore cluster name validation of connected nodes.
         * (since 0.19.4)
         * 
         * client.transport.ping_timeout
         * 
         * The time to wait for a ping response from a node. Defaults to 5s.
         * 
         * client.transport.nodes_sampler_interval
         * 
         * How often to sample / ping the nodes listed and connected. Defaults
         * to 5s.
         * 
         * 
         */

    }

}
