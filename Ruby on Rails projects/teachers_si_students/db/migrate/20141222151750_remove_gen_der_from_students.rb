class RemoveGenDerFromStudents < ActiveRecord::Migration
  def change
    remove_column :students, :gen_der, :integer
  end
end
