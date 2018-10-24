package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import spark.Session;
import spark.TemplateEngine;

import java.util.*;

import static com.webcheckers.ui.HomePageRenderer.*;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Zeke Miller
 */
public class HomePageRendererTest {

    @Test
    public void testNullTemplateEngine() {
        Executable testBothNull = () -> new HomePageRenderer( null, null );
        assertThrows( NullPointerException.class, testBothNull,
                      "expected an exception from null arguments" );
        PlayerLobby lobby = new PlayerLobby();
        Executable testEngineNull = () -> new HomePageRenderer( null, lobby );
        assertThrows( NullPointerException.class, testEngineNull,
                      "expected an exception from null engine" );
    }

    @Test
    public void testNullPlayerLobby() {
        Executable testBothNull = () -> new HomePageRenderer( null, null );
        assertThrows( NullPointerException.class, testBothNull,
                      "expected an exception from null arguments" );

        TemplateEngine engine = mock(TemplateEngine.class);
        Executable testLobbyNull = () -> new HomePageRenderer( engine, null );
        assertThrows( NullPointerException.class, testLobbyNull,
                      "expected an exception from null lobby" );
    }

    @Test
    public void testRenderSignedInNormal() {

        final String expectedViewName = HOME_VIEW_NAME;
        final String testName = "name";

        Player playerMock = mock( Player.class );
        when( playerMock.getName() ).thenReturn( testName );

        Collection<String> players = Arrays.asList( "player1", "player2" );
        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        when( lobbyMock.getPlayer( testName ) ).thenReturn( playerMock );
        when( lobbyMock.getAllPlayers() ).thenReturn( players );

        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( testName );

        Renderer renderer = new HomePageRenderer( engineMock, lobbyMock );
        renderer.render( sessionMock );

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( expectedViewName );
        tester.assertViewModelAttribute( PLAYER_NAME_ATTR, testName );
        tester.assertViewModelAttribute( SIGNED_IN_ATTR, true );
        tester.assertViewModelAttribute( PLAYER_LIST_ATTR, players );
    }

    @Test
    public void testRenderNotSignedIn() {

        final String expectedViewName = HOME_VIEW_NAME;
        final String testName = null;

        Player playerMock = mock( Player.class );
        when( playerMock.getName() ).thenReturn( testName );

        Collection<String> players = Arrays.asList( "player1", "player2" );
        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        when( lobbyMock.getPlayer( testName ) ).thenReturn( playerMock );
        when( lobbyMock.getAllPlayers() ).thenReturn( players );

        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( testName );

        Renderer renderer = new HomePageRenderer( engineMock, lobbyMock );
        renderer.render( sessionMock );

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( expectedViewName );
        tester.assertViewModelAttribute( PLAYER_NAME_ATTR, null );
        tester.assertViewModelAttribute( SIGNED_IN_ATTR, Boolean.FALSE );
        tester.assertViewModelAttribute( PLAYER_LIST_ATTR, players );
    }

    @Test
    public void testNullPlayersFromLobby() {

        final String expectedViewName = HOME_VIEW_NAME;
        final String testName = "name";

        Player playerMock = mock( Player.class );
        when( playerMock.getName() ).thenReturn( testName );

        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        when( lobbyMock.getPlayer( testName ) ).thenReturn( playerMock );
        when( lobbyMock.getAllPlayers() ).thenReturn( null );

        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( testName );

        Renderer renderer = new HomePageRenderer( engineMock, lobbyMock );
        renderer.render( sessionMock );

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( expectedViewName );
        tester.assertViewModelAttribute( PLAYER_NAME_ATTR, testName );
        tester.assertViewModelAttribute( SIGNED_IN_ATTR, true );
        tester.assertViewModelAttribute( PLAYER_LIST_ATTR, null );
    }


    @Test
    public void testRenderNormalNullMap() {
        final String expectedViewName = HOME_VIEW_NAME;
        final String testName = "name";

        Player playerMock = mock( Player.class );
        when( playerMock.getName() ).thenReturn( testName );

        Collection<String> players = Arrays.asList( "player1", "player2" );
        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        when( lobbyMock.getPlayer( testName ) ).thenReturn( playerMock );
        when( lobbyMock.getAllPlayers() ).thenReturn( players );

        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( testName );

        Renderer renderer = new HomePageRenderer( engineMock, lobbyMock );
        renderer.render( sessionMock, null );

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( expectedViewName );
        tester.assertViewModelAttribute( PLAYER_NAME_ATTR, testName );
        tester.assertViewModelAttribute( SIGNED_IN_ATTR, true );
        tester.assertViewModelAttribute( PLAYER_LIST_ATTR, players );
    }

    @Test
    public void testNotOverwritingTitle() {
        final String expectedViewName = HOME_VIEW_NAME;
        final String testName = "name";
        final String title = "testing Title";

        Map<String, Object> map = new HashMap<>();
        map.put( TITLE_ATTR, title );

        Player playerMock = mock( Player.class );
        when( playerMock.getName() ).thenReturn( testName );

        Collection<String> players = Arrays.asList( "player1", "player2" );
        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        when( lobbyMock.getPlayer( testName ) ).thenReturn( playerMock );
        when( lobbyMock.getAllPlayers() ).thenReturn( players );

        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( testName );

        Renderer renderer = new HomePageRenderer( engineMock, lobbyMock );
        renderer.render( sessionMock, map );

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( expectedViewName );
        tester.assertViewModelAttribute( PLAYER_NAME_ATTR, testName );
        tester.assertViewModelAttribute( SIGNED_IN_ATTR, true );
        tester.assertViewModelAttribute( PLAYER_LIST_ATTR, players );
        tester.assertViewModelAttribute( TITLE_ATTR, title );
    }

    @Test
    public void testRenderNullSession() {
        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        TemplateEngine engineMock = mock( TemplateEngine.class );
        Executable test =
                () -> new HomePageRenderer( engineMock, lobbyMock )
                        .render( null );

        assertThrows( NullPointerException.class, test,
                      "expected an exception from null Session" );
    }


}
