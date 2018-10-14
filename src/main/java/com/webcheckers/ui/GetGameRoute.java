package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * The UI Controller to GET the Game page.
 *
 * @author Spencer Fleming
 */
public class GetGameRoute implements Route {

    private Renderer gameRenderer;

    /*
     * constructs new GetGameRoute
     * @param renderer GamePageRenderer
     * */
    public GetGameRoute(Renderer renderer){
        this.gameRenderer = renderer;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        GameCenter gameCenter = GameCenter.getInstance();
        Map<String, Object> map = new HashMap<>();
        String name = request.session().attribute( "name" );
        String opponentName = request.queryParams("opponent");

        boolean gameAdded = gameCenter.addGame(name, opponentName);

        if (!gameAdded) {
            //false if one of players is null or already in game
            return null;
        }

        map.put("title", name + " vs. " + opponentName);

        //request.session().attribute("board", gameCenter.getGame(name));

        return gameRenderer.render(request.session(), map);
    }
}
