package Docusign;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Authenticator {

    private int  timeToLive;
    private Map<String, Integer> tokenMap;

    public void AuthenticationManager(int timeToLive) {
        this.timeToLive = timeToLive;
        this.tokenMap = new HashMap<>();
    }

    public void generate(String tokenId, int currentTime) {
        tokenMap.put(tokenId, currentTime + timeToLive);
    }

    public void renew(String tokenId, int currentTime) {
        if (tokenMap.containsKey(tokenId) && tokenMap.get(tokenId) > currentTime) {
            tokenMap.put(tokenId, currentTime + timeToLive);
        }
    }

    public int countUnexpiredTokens(int currentTime) {
        Iterator<Map.Entry<String, Integer>> iterator = tokenMap.entrySet().iterator();
        int count = 0;

        while (iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() <= currentTime){
                iterator.remove();
            } else {
                count++;
            }
        }

        return count;
    }
}
