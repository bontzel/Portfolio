"use strict";

var React = require("react/addons");
var Day = require("./day.jsx");

var Week = React.createClass({



    render: function() {
      var cm = this.props.currentMonth;
      var getDays = function(date) {

        var parseDate = function(date) {
          var days = ["Sun","Mon","Tue","Wen","Thu","Fri","Sat"];
          var months = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];

          var parsedDate = days[date.getDay()] + " " + months[date.getMonth()] + " " +
                           date.getDate().toString() + " " + date.getFullYear().toString();

          return parsedDate;
        };

        var pDate = parseDate(date);
        return(
            <Day
              key = {count++}
              date = {pDate}
              currentMonth = {cm} />
        );
      };

      var count=0;

      return (
        <tr>
          {this.props.days.map(getDays)}
        </tr>
      );
    },
});

module.exports = Week;
