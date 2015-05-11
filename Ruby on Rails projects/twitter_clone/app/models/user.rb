class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable
  has_many :tweets

  has_many :followed_users, class_name: "User",
                            foreign_key: "followed_by_user_id"
  belongs_to :followed_by_user, class_name: "User"
end
