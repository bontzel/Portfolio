"use strict";

var React = require("react/addons");
var Router = require("react-router");
var Link          = Router.Link;
var Week = require("./week.jsx");

var NextMonth = React.createClass({
  render: function() {
    return(
      <Link className="next-month" to="months"
            params={{monthNumber:this.props.month + 1}} > &gt; </Link>
    );
  },
});

var PrevMonth = React.createClass({
  render: function() {
    return(
      <Link className="prev-month" to="months"
            params={{monthNumber:this.props.month - 1}} > &lt; </Link>
    );
  },
});


var Month = React.createClass({

  mixins: [Router.State, Router.Navigation],

  

  render: function() {
    var currentMonth;
    var monthNames = ["January", "February", "March","April","May","June","July","August","September","October","November","December"];
    var month = new Array(6);
    var time = new Date();


    if(this.getParams().monthNumber) {
      currentMonth = this.getParams().monthNumber;
    } else {
      currentMonth = Date.today().getMonth();
    }

    time.setMonth(currentMonth,1);
    var date = new Date(time);
    var dateAux = new Date(time);


    if(date.getDay() !== 0) {
      date = date.add(date.getDay() * -1).day();
    }

    for(var i = 0; i < 6; i++) {
      month[i] = new Array(7);
      for(var j = 0; j < 7; j++) {
        month[i][j] = new Date(date);
        date = date.add(1).day();
      }
    }

    var drawWeeks = function(week) {

      return(
        <Week
          key = {count++}
          days = {week}
          currentMonth = {currentMonth} />
      );
    };

    var count = 0;

    return (
      <table>
        <tr>
          <th>
            <PrevMonth month={dateAux.getMonth()}/>
          </th>
          <th colSpan="5">
            {monthNames[dateAux.getMonth()]}, {dateAux.getFullYear()}
          </th>
          <th>
            <NextMonth month={dateAux.getMonth()}/>
          </th>
        </tr>
        <tr>
          <th>
            Sun
          </th>
          <th>
            Mon
          </th>
          <th>
            Tue
          </th>
          <th>
            Wen
          </th>
          <th>
            Thu
          </th>
          <th>
            Fri
          </th>
          <th>
            Sat
          </th>
        </tr>
        {month.map(drawWeeks)}
      </table>
    );
  },
});

module.exports = Month;
