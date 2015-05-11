class ClassroomHasTeacher < ActiveRecord::Base
	belongs_to :clasroom
	belongs_to :teacher
end
