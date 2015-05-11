class Classroom < ActiveRecord::Base
	has_many :students
	has_many :classroom_has_teachers
	has_many :teachers, :through => :classroom_has_teachers
	accepts_nested_attributes_for :students, :allow_destroy => true
end
