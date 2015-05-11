class Admin::ArticlesController < ApplicationController
	http_basic_authenticate_with name: "dhh", password: "secret"

	def index
		@articles = Article.all
	end

	def new 
		@article = Article.new
	end

	def create
		article = Article.new
		article.title = params[:article][:title]
		article.content = params[:article][:content]

		if article.save
			flash[:notice] = "Article was saved successfully"
		else
			flash[:notice] = "Unfortunately there were some errors while saving the article: #{article.errors.to_s}"
		end

		redirect_to "/admin/articles"
	end

	def edit
		@article = Article.find(params[:id])
	end

	def update
		@article = Article.find(params[:id])

		if @article.update(article_params)
			redirect_to @article
		else
			render 'edit'
		end
	end

	def destroy
		@article = Article.find(params[:id])
		@article.destroy

		redirect_to admin_articles_path
	end

	def article_params
		params.require(:article).permit(:title, :content)
	end




end
