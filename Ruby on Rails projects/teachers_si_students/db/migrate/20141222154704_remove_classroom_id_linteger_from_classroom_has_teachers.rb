class RemoveClassroomIdLintegerFromClassroomHasTeachers < ActiveRecord::Migration
  def change
    remove_column :classroom_has_teachers, :classroom_idLintger, :string
  end
end
