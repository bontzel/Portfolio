class RenameGenderToGenDer < ActiveRecord::Migration
  def change
  	rename_column :students, :gender, :gen_der
  end
end
