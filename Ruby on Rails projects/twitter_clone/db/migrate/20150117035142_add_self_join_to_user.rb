class AddSelfJoinToUser < ActiveRecord::Migration
  def change
    add_column :users, :followed_by_user_id, :integer
  end
end
