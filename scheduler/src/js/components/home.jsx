"use strict";

var React = require("react/addons");
var Date =  require("datejs");
var Month = require("./month.jsx");

var Home = React.createClass({
  render: function() {
    return(
          <Month/>
        );
  },
});

module.exports = Home;
