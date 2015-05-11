class TweetsController < ApplicationController
  def index
  end

  def create
    @user = User.find_by_id(current_user.id)
    @user.tweets.create(tweet_params)
    redirect_to user_path(@user)
  end

  private
  def tweet_params
    params.require(:tweet).permit(:text)
  end
    
end
