'use strict';

var React  = require('react/addons');
var Router = require('react-router');

var Route        = Router.Route;
var DefaultRoute = Router.DefaultRoute;
var NotFoundRoute = Router.NotFoundRoute;


var App = require('./app');
var Home = require('./components/home.jsx');
var DayView = require('./components/dayView.jsx')


module.exports = (
	<Route handler={App} path="/">
		<DefaultRoute name="home"  handler={Home} />
    <Route name="months" path="/month/:monthNumber" handler={Home} />
		<Route name="dayView" path="/day/:date" handler={DayView} />
	</Route>
);
