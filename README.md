### Play Shiro Integration ###

This is a very simple integration between Apache Shiro, an authorization and authentication platform, and Play 2.0.

To start the application, check the project out from Github, make sure you have Play 2.0 installed, then type

    play run

And run through the DB evolutions needed.  Then go to http://localhost:9000.

### How it works ###

When the Global object is called, it configures Shiro's security manager with security.SampleRealm.

When you hit the page, Play will look for a token called "email" in request.session, and find the email address corresponding to that token.  That token is only set if we have successfully logged in.

When you login, User.authenticate will use Shiro's SecurityUtils.currentUser, call login on that, and that will go back to the Realm for digest checking.  (Note that we use Jasypt to deal with password complexity.)

When you logout, User.logout will call Shiro to invalidate the current session.

### Warning ###

Shiro assumes a stateful session strategy, which goes against Play's stateless application.  You may want to check the Reference Manual for how to handle this.

Shiro's Reference Manual discusses "stateless" sessions using Shiro's 1.2 API: as of this time of writing (1/8/2012) 1.2 is still in beta.
