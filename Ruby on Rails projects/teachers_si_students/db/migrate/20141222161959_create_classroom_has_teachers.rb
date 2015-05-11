class CreateClassroomHasTeachers < ActiveRecord::Migration
  def change
    create_table :classroom_has_teachers do |t|
      t.integer :classroom_id
      t.integer :teacher_id

      t.timestamps
    end
  end
end
