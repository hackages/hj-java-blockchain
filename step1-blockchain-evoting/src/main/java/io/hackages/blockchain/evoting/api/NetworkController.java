package io.hackages.blockchain.evoting.api;

import io.hackages.blockchain.evoting.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/network")
public class NetworkController {

    @Value("target.base.url")
    String baseUrl;

    @Autowired
    private VotingService votingService;

    @RequestMapping(value = "/registerNodes", method = RequestMethod.POST)
    public ResponseEntity<Object> registerNodes(@RequestBody List<String> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return new ResponseEntity<Object>("Error: Please supply a valid list of nodes", HttpStatus.BAD_REQUEST);
        }

        for (String node : nodes) {
            try {
                votingService.registerNode(new URL(baseUrl.concat(node)));
            } catch (MalformedURLException e) {
                return new ResponseEntity<Object>("Error: Invalid node " + node + ", Please supply a valid node", HttpStatus.BAD_REQUEST);
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", "New nodes have been added to the network");

        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }
}