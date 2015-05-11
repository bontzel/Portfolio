class AddDescriptionToTodo < ActiveRecord::Migration
  def change
    add_column :todos, :description, :text
  end
end
