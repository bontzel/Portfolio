class UsersController < ApplicationController
  before_action :authenticate_user!

  def show
    @user = current_user


    @userTweets = current_user.tweets
    

    if(!current_user.followed_users.empty?)
      @others = current_user.followed_users
      for other in @others
        @tweets = @userTweets + other.tweets
      end
      @tweets.sort! { |a,b| a.created_at <=> b.created_at }
    else
      @tweets = @userTweets
    end

    if(!params[:q].blank?)
      @tweets = @tweets.select {|a| a.text[/#{params[:q]}/] }
    end

  end

  def home
    redirect_to "/users/" + current_user.id.to_s
  end
  
  def followUser
    @user = current_user
    @user.update_attribute(:followed_users, @user.followed_users << User.find_by_id(params[:id].to_i))
    redirect_to user_path(@user)
  end

end
   
