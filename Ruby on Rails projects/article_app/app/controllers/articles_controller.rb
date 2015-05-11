class ArticlesController < ApplicationController

	def index
		if(params[:q].blank?)
			@articles = Article.all
		else
			@articles = Article.where("title like '%" + params[:q] + "%'")
		end
	end

	def show
		@article = Article.find(params[:id])

		if @article.number_of_views
			@article.update_attributes(:number_of_views => @article.number_of_views + 1)
		else
			@article.update_attributes(:number_of_views => 1)
		end
	end

end
