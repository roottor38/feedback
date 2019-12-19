package feedback.dao;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONArray;

import feedback.model.dto.UserResDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DaoDTO {
	private SearchRequest request;
	private SearchSourceBuilder builder;
	private AggregationBuilder aggr;
	private UserResDTO UserRes;
	private JSONArray jsonArray;
	

	

}
