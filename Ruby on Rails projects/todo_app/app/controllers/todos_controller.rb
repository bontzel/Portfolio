class TodosController < ApplicationController
  # action
  def index
    @todos = Todo.all
  end

  def new
    @todo = Todo.new
  end 

  def create
    #Parameters: {"utf8"=>"✓", "authenticity_token"=>"44yU6B3tCRzEW360qqG4H/tScGWlOm1bXp7ODGH1AWA=", 
    #  "todo"=>{"title"=>"sada"}, "commit"=>"Create"}
    # params[:todo][:title] => "sada"

    # Versiunea 1
    # todo = Todo.new(:title => params[:todo][:title])

    # Versiunea 2
    # todo_params = params.require(:todo).permit(:title)
    # todo = Todo.new(todo_params)

    # Versiunea 3
    todo = Todo.new
    todo.title = params[:todo][:title]
    todo.description = params[:todo][:description]

    if todo.save
      flash[:notice] = "Todo was saved successfully"
    else
      flash[:notice] = "Unfortunately there were some errors while saving the todo: #{todo.errors.to_s}"
    end

    redirect_to "/todos"
  end

  def delete
    Todo.where(:title => params[:q]).destroy_all

    redirect_to "/todos"
  end

  def previous
    @todos = Todo.last(3)
  end

  def update
    @todos = Todo.find(params[:title])

    @todos.update(todo_params)
  end

  def show
    @todo = Todo.find(params[:id])
  end

  private

  def todo_params
    params.require(:todo).permit(:title)
  end
end
