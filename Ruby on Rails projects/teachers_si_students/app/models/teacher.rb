class Teacher < ActiveRecord::Base
	has_many :classroom_has_teachers
	has_many :classrooms, :through => :classroom_has_teachers
end
