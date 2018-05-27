package com.tzg.entitys.kff.article;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<Article, java.lang.Integer> {

	Article findByPostId(Integer postId);

	Article selectArticleByPostId(Integer postId);

}
