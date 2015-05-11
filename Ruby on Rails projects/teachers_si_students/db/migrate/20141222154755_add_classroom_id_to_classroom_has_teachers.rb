class AddClassroomIdToClassroomHasTeachers < ActiveRecord::Migration
  def change
    add_column :classroom_has_teachers, :clasroom_id, :integer
  end
end
