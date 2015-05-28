"use strict";

var React = require("react/addons");
var Firebase = require("firebase");

var Db = new Firebase("https://scheduler-btzl.firebaseio.com/");
var DbNotes = Db.child("notes");

var NotesForm = React.createClass({

  getInitialState: function() {
		return {
  		noteText: "",
		};
	},

  onTextChange: function(e) {
		this.setState({noteText: e.target.value});
	},

  handleSubmit: function(e) {
		e.preventDefault();

		if(this.state.noteText.length === 0) {
			alert("Note box must not be empty!");
			return false;
		}
    var note  = {
			text: this.state.noteText,
      date: this.props.date,
		};

		DbNotes.push(note);

		this.setState({
  			noteText: "",
		});
  },


  render: function() {
    return(
      <form className="notes-form" onSubmit={this.handleSubmit}>
        <label> Add a note: </label>
        <textarea name="notes-input" placeholder="Write your note here.." rows="5" onChange= {this.onTextChange } />
        <input type="submit" className="submit_button" />
      </form>
    );
  },
});

module.exports = NotesForm;
