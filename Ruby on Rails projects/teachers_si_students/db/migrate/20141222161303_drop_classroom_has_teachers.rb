class DropClassroomHasTeachers < ActiveRecord::Migration
  def change
  	drop_table :classroom_has_teachers
  end
end
