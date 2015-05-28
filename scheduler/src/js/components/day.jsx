"use strict";

var React        = require("react/addons");
var Router = require("react-router");
var Datejs = require("datejs");
var MONTH_BEGIN = 4;
var MONTH_END = 7;
var DATE_BEGIN = 8;
var DATE_END = 10;

var Day = React.createClass({

  mixins: [Router.State, Router.Navigation],

  onClick: function() {
    this.transitionTo("dayView",{date: this.props.date});
  },

  getMonth: function(date) {
    return date.slice(MONTH_BEGIN,MONTH_END);
  },

  getDate: function(date) {
    return date.slice(DATE_BEGIN,DATE_END);
  },

  render: function() {
    var months = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
    var isOut = this.getMonth(this.props.date) !== months[this.props.currentMonth];
    var date = new Date(this.props.date);
    var isToday = date.is().today();
    var cx = React.addons.classSet;

    var classes = cx({
      "is-today": isToday,
      "day": !isOut,
      "day-outer-month": isOut,
    });

    return(
      <td className = {classes} onClick = {this.onClick}>
        {this.getDate(this.props.date)}
      </td>
    );
  },
});

module.exports = Day;
