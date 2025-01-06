package com.example.fs.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fs.knowledge.mapper.KnowledgeBaseMapper;
import com.example.fs.knowledge.pojo.knowledgeBase;
import com.example.fs.knowledge.service.KnowledgeBaseService;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeBaseServiceimpl extends ServiceImpl<KnowledgeBaseMapper, knowledgeBase> implements KnowledgeBaseService {
}
