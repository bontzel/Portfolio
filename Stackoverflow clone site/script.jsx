var Router        = window.ReactRouter;
var Link          = Router.Link;
var Route         = Router.Route;
var DefaultRoute  = Router.DefaultRoute;
var NotFoundRoute = Router.NotFoundRoute;
var RouteHandler  = Router.RouteHandler;

var Db          = new Firebase("https://stack-flow-2.firebaseio.com/");
var DbQuestions = Db.child('questions');
var DbAnswers   = Db.child('answers');
var DbTags = Db.child('tags');


var App = React.createClass({
	render: function() {
		return (
				<RouteHandler/>
		);
	},
});

var Pagination = React.createClass({
	render: function() {
		return (
			<div className="Pagination">
				{this.renderPrev()}
				{this.renderNext()}
			</div>
		);
	},

	renderPrev: function() {
		if(this.isFirstPage()) {
			return null;
		}

		return <Link className="Pagination-prev" to="questions"
					 params = { { pageNumber: this.props.currentPage - 1} }>
			        &lt; Prev
			   </Link>;
	},

	renderNext: function() {
		if(this.isLastPage()) {
			return null;
		}
		return <Link className="Pagination-next"
					 to="questions"
					 params={{ pageNumber: this.props.currentPage + 1}}
					 > Next &gt;
			   </Link>;
	},

	isFirstPage: function() {
		return this.props.currentPage === 0;
	},

	isLastPage: function() {
		var totalPages = Math.ceil(this.props.itemCount / this.props.perPage);
		return(this.props.currentPage + 1) >= totalPages;
	},
});

var AnswersList = React.createClass({
	render: function() {
		var answers = [];
		if(this.props.answersList.length !== 0)
			answers = this.props.answersList;

		var getAnswers = function(answer) {
			return (
				<li><label> {answer.user}: </label>
					<p> {answer.text} </p> </li>
			);
		};

		return (
			<ul className="answers_list">
				{answers.map(getAnswers)}
			</ul>
		);
	},
});

var AnswerForm = React.createClass({

	getInitialState: function() {
		username = '';
  		answerText = '';
		return {
			username: '',
  			answerText: '',
		};
	},

	onTextChange: function(e) {
		this.setState({answerText: e.target.value});
	},

	onUsernameChange: function(e) {
		this.setState({username: e.target.value});
	},

	handleSubmit: function(e) {
		e.preventDefault();
		var username;
		if(this.state.username.length === 0)
			username = 'Guest';
		else
			username = this.state.username;

		if(this.state.answerText.length === 0) {
			alert('Answer box must not be empty!');
			return false;
		}

		var answer = {
			user: username,
			questionId: this.props.questionId,
			text: this.state.answerText,
		};

		DbAnswers.push(answer);

		this.setState({
			username: '',
  			answerText: '',
		});

	},

	render: function() {
		return (
			<form className="answer_form" onSubmit={this.handleSubmit} >
				<label> Username: </label><input type="text" placeholder="Optional" onChange={ this.onUsernameChange } />
				<input type="submit" className="submit_button" />
				<textarea name="answer input" placeholder="Write your answer here.." rows="5" onChange= {this.onTextChange } />
			</form>
		);
	},
});

var QuestionView = React.createClass({

  mixins: [Router.State, Router.Navigation],

  getInitialState: function() {

    return {title: '',
    		body: '',
    		answers: [],
    		};
  },

  componentWillMount: function() {
    var id = this.getParams().questionId;
    DbQuestions
      .child(id)
      .on('value', this.onQuestionLoaded);

    DbAnswers
   		.orderByChild("questionId")
   		.equalTo(id)
   		.on('value', this.onAnswersLoaded);
  },

  render: function() {
    return (
    	<div className="centeringContainer">
	      <div className="Question">
	        <h2>{this.state.title}</h2>
	        <p>{this.state.body}</p>
	      </div>
	      <AnswersList answersList = {this.state.answers} />
	      <AnswerForm questionId = {this.getParams().questionId} />
	      <a className="back_button" href="#" onClick={this.onClickBack}>Back</a>
	    </div>
    );
  },

  onClickBack: function(event) {
    event.preventDefault();
    this.goBack();
  },

  onQuestionLoaded: function(question) {
    this.setState({ title: question.val().title, body: question.val().body });
  },

  onAnswersLoaded: function(answers) {
  	var list = [];
  	answers.forEach(function(answer){
  		list.push(answer.val());
  	});

  	this.setState({answers: list,});
  },

});

var QuestionList = React.createClass({

	mixins: [ Router.State ],

	getInitialState: function() {
		return {
			perPage: 5,
			questions: [],
			questionsBackup: [],
			activeTags: [],
		};
	},

	onQuestionsLoaded: function(dataSnapshot) {
		var questions = [];
		dataSnapshot.forEach(function(question) {
			questions.push(question);
		});

		this.setState({ questions: questions,
						questionsBackup: questions,
		 });
	},

	componentWillMount: function() {
		DbQuestions.orderByKey().on('value', this.onQuestionsLoaded);
	},

	getCurrentPage: function() {
		var pageNumber = this.getParams().pageNumber;
		return  pageNumber ? parseInt(pageNumber, 10) : 0;
	},

	renderCurrentPage: function() {
		var startIndex = this.state.perPage * this.getCurrentPage();
		var endIndex = startIndex + this.state.perPage;

		return this.state.questions.slice(startIndex, endIndex).map(function(question) {
	      return (
	        <li key={'question-' + question.key()}>
	          <Link to="question" params={ { questionId: question.key() } }>{question.val().title}</Link>
	        </li>
	      );
	    });
	},

	updateActiveTagsAndQuestions: function(tag, tagToggleState, allTags ) {
		this.list = this.state.activeTags;
		var questions;

		if(tagToggleState) {
			this.list.push(tag);
		} else {
			var idx =  this.list.indexOf(tag);
			this.list.splice(idx, 1);
		}

		if(this.list.length > 0) {
			ids = this.getTaggedQuestions(this.list, allTags);
			questions = this.getQuestionsFromDB(ids);
		} else {
			questions = this.state.questionsBackup;
		}

		this.setState({ activeTags: this.list,
						questions: questions,
		});
	},

	getQuestionsFromDB: function(ids) {
		var questions = [];

		ids.forEach(function(id){
			var path = DbQuestions + "/" + id;
			var dbLink =  new Firebase(path);

			dbLink.once("value", function(data){
				questions.push(data);
			});
		});

		return questions;

	},

	getTaggedQuestions: function(activeTags, tags) {

		var viableQuestions = [];

		activeTags.forEach(function(el){
			tags.forEach(function(tagsEl) {
				if(tagsEl.name === el)
					if(viableQuestions.length === 0) {
						//console.log(tagsEl.questions);
						viableQuestions = tagsEl.questions;
					}
					else {
						viableQuestions = viableQuestions.filter(function(n) {
    						return tagsEl.questions.indexOf(n) != -1;
						});
					}
			});
		});

		return viableQuestions;
	},

	render: function() {
		return (
			<div className="question_box">
				<TagsList returnActiveTags = {this.updateActiveTagsAndQuestions}/>
				<ul className="question_list">
					{this.renderCurrentPage()}
				</ul>
				<Pagination currentPage={this.getCurrentPage()}
							itemCount = {this.state.questions.length}
							perPage = {this.state.perPage}
				/>
				<Link className="ask_button" to="ask_view"> Ask a question! </Link>
			</div>
		);
	},
});

var Tag = React.createClass({

	getInitialState: function() {
		return{
			toggle: false,
		};
	},

	onClick: function() {
		this.props.returnActiveTag(this.props.name, !this.state.toggle, this.props.tags);

		this.setState({toggle: !this.state.toggle,});
	},

	render: function() {
		var cx = React.addons.classSet;
		var cls = cx({
			'active_tags':this.state.toggle,
		});


		return(
			<li className={cls} onClick = {this.onClick} > {this.props.name} </li>
		);
	},
});

var TagsList = React.createClass({

	getInitialState: function(){
		return {
			tags: [],
		};
	},

	componentWillMount: function() {
		DbTags
			.orderByKey()
			.on('value', this.onTagsLoaded);
	},

	onTagsLoaded: function(tags) {
		var list = [];
		tags.forEach(function(tag){
			list.push(tag.val());
		});

		this.setState({tags: list,});
	},

	render: function() {
		aux = this.props.returnActiveTags;
		aux2 = this.state.tags;
		var getTags = function(el){

			return(
				<Tag name={el.name}
				     returnActiveTag = {aux}
				     tags = {aux2}
				/>
				//<li onClick={this.onClick} >  {el.name} </li>
			);
		};
		return (
		<div className="tags_box">
			<ul className="tags_list">
				{this.state.tags.map(getTags)}
			</ul>
		</div>
		);
	},
});

var AskFormView = React.createClass({

	getInitialState: function() {
			title = '';
			body = '';
			tags = [];
			return {
				title:'',
				body:'',
				tags:[],
			};
	},

	contextTypes: {
    router: React.PropTypes.func
  },

	componentDidMount: function() {
		//console.log(this.context.router);
	},


	onChangeTitle: function(e) {
		this.setState({
			title: e.target.value,
		});
	},

	onChangeBody: function(e) {
		this.setState({
			body: e.target.value,
		});
	},

	onChangeTags: function(e) {
		this.setState({
			tags: e.target.value,
		});
	},

	parseTags: function() {
		var seps = /[^\s\.,!?;]+/g;

		if(this.state.tags.length > 0) {
			var tags = this.state.tags.match(seps);
			return tags;
		} else {
			return [];
		}
	},

	handleSubmit: function(e) {
		e.preventDefault();
		var currentdate = new Date();

		//var printDate = currentdate.getDate() + "/"  + (currentdate.getMonth()+1)  + "/"  + currentdate.getFullYear() + " @ "  + currentdate.getHours() + ":"  + currentdate.getMinutes();

		var tags = this.parseTags();
		var orderDate = Firebase.ServerValue.TIMESTAMP; //best pratice for ordering by time according to API, dunno how to parse it tho
		var questionId;

		if(this.state.title &&
			 this.state.body &&
			 this.state.title.trim().length > 0 &&
		   this.state.body.trim().length > 0
		  ) {

			var question = {
				title: this.state.title,
				body: this.state.body,
				rating: 0,
				date: orderDate,
			};

			questionId = DbQuestions.push(question).key();
		} else
			alert('Title and Body are required!');

		tags.forEach(function(tag) {

			DbTags.orderByChild('name')
						.equalTo(tag)
						.once('value',function(data){
							if(data.val() !== null) {
								var key = Object.keys(data.val())[0];
								var tagRef = DbTags.child(key.toString());
								var newQuestionIds;

								tagRef.child('questions')
											.once('value',function(tagData){

												newQuestionIds = tagData.val();
												newQuestionIds.push(questionId);
												tagRef.set({
														name: tag,
														questions: newQuestionIds,
												});
								});
							} else {
								var newQuestions = [];
								newQuestions.push(questionId);
								var newTag = {
									name: tag,
									questions: newQuestions,
								};

								DbTags.push(newTag);
							}
						});
		});

		this.context.router.transitionTo('/question/' + questionId);
	},

	render: function() {
		return (
		<div className="centering_container">
			<div className="main_container">
				<form className="ask_form" onSubmit={this.handleSubmit}>
					<div className="input_container">
						<label> Title: </label><input onChange={this.onChangeTitle} type="text"></input>
					</div>
					<div className="input_container">
						<label> Body: </label><textarea onChange={this.onChangeBody}></textarea>
					</div>
					<div className="input_container">
						<label> Tags: </label><textarea onChange={this.onChangeTags} placeholder="Optional"></textarea>
					</div>
					<input className="submit_button" type="submit" value="submit"></input>
				</form>
				<a className="back_button" href="#" onClick={this.goBack}> Back </a>
			</div>
		</div>
		);
	},
});

var Home = React.createClass({

	render: function() {
		return (
		<div className="centering_container">
			<div className="main_container">
				<QuestionList/>
			</div>
		</div>
		);
	},
});

var NotFound = React.createClass({
  render: function() {
    return <h1 className="error">Page not found!</h1>;
  },
});

var routes = (
	<Route handler={App} path="/">
		<DefaultRoute name="home" handler={Home} />
		<Route name="questions" path="/page/:pageNumber" handler={Home} />
		<Route name="question" path="/question/:questionId" handler={QuestionView} />
		<Route name="ask_view" path="/ask_form" handler={AskFormView} />
		<NotFoundRoute handler={NotFound}/>
	</Route>
);

Router.run(routes, function(Handler){
	React.render(<Handler/>, document.getElementById('app'));
});
