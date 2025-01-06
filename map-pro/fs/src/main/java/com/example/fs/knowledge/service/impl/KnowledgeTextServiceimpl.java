package com.example.fs.knowledge.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fs.knowledge.mapper.KnowledgeTextMapper;
import com.example.fs.knowledge.pojo.AddKnowledgeTextDTO;
import com.example.fs.knowledge.pojo.DeleteKnowledgeTextDTO;
import com.example.fs.knowledge.pojo.KnowledgeText;
import com.example.fs.knowledge.pojo.UpdateKnowledgeTextDTO;
import com.example.fs.knowledge.service.KnowledgeTextService;
import org.springframework.stereotype.Service;

/**
 * @author Adss
 * @data 2025/1/6 15:26
 */
@Service
public class KnowledgeTextServiceimpl extends ServiceImpl<KnowledgeTextMapper, KnowledgeText> implements KnowledgeTextService {
    @Override
    public void add(AddKnowledgeTextDTO dto) {
        save(BeanUtil.toBean(dto, KnowledgeText.class));
    }

    @Override
    public void delete(DeleteKnowledgeTextDTO dto) {
        update(
                Wrappers.lambdaUpdate(KnowledgeText.class)
                        .eq(KnowledgeText::getId, dto.getId())
                        .eq(KnowledgeText::getRemoved, false)
                        .set(KnowledgeText::getRemoved, true)
        );
    }

    @Override
    public void update(UpdateKnowledgeTextDTO dto) {
        String id = dto.getId();
        String title = dto.getTitle();
        String to = dto.getTo();
        String knowledgeBaseId = dto.getKnowledgeBaseId();
        update(
                Wrappers.lambdaUpdate(KnowledgeText.class)
                        .eq(KnowledgeText::getId, id)
                        .eq(KnowledgeText::getRemoved, false)
                        .set(title != null, KnowledgeText::getTitle, title)
                        .set(to != null, KnowledgeText::getTo, to)
                        .set(knowledgeBaseId != null, KnowledgeText::getKnowledgeBaseId, knowledgeBaseId)
        );
    }
}

