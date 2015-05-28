"use strict";
var React = require("react/addons");
var Router = require("react-router");

var NotesForm = require("./notesForm.jsx");
var NoteBox = require("./noteBox.jsx");

var Firebase = require("firebase");
var DbNotes = new Firebase("https://scheduler-btzl.firebaseio.com/notes");
var DATE_BEGIN = 8;
var DATE_END = 10;
var DaysHaveNotes = new Array(32);

var DayView = React.createClass({

  mixins: [Router.State, Router.Navigation],

  parseDate: function(date) {
    var days = ["Sun","Mon","Tue","Wen","Thu","Fri","Sat"];
    var months = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];

    var parsedDate = days[date.getDay()] + " " + months[date.getMonth()] + " " +
                     date.getDate().toString() + " " + date.getFullYear().toString();

    return parsedDate;
  },

  getInitialState: function() {
    return {
      notes: [],
    };
  },

  getDate: function(date) {
    return date.slice(DATE_BEGIN,DATE_END);
  },

  onNotesLoaded: function(notes) {
  	var list = [];
  	notes.forEach(function(note){
  		list.push(note.val());
  	});

  	this.setState({notes: list,});
  },

  componentWillMount: function() {
    var date = this.getParams().date;

    DbNotes
   		.orderByChild("date")
   		.equalTo(date)
   		.on("value", this.onNotesLoaded);
  },

  render: function(){
    var date = this.getParams().date;
    var getNotes = function(note) {
      return(
        <NoteBox text = {note.text}
          date = {date} />
      );
    };

    if(this.state.notes.length > 0) {
      DaysHaveNotes[this.getDate(this.getParams().date)] = true;
    }

    return(
      <div className="day-view">
        <header>
          {this.getParams().date}
        </header>
        <div className="notes-container">
          {this.state.notes.map(getNotes)}
        </div>
        <NotesForm date = {this.getParams().date} />
        <a className="back_button" href="#" onClick={this.onClickBack}>Back</a>
      </div>
    );
  },
});

module.exports = DaysHaveNotes;
module.exports = DayView;
