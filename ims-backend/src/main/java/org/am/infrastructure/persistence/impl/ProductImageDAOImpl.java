package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.ProductImage;
import org.am.domain.catalog.exceptions.NotFound.ProductNotFoundException;
import org.am.infrastructure.persistence.api.ProductImageDAO;
import org.am.infrastructure.persistence.converters.ProductImageConverter;
import org.am.infrastructure.persistence.converters.ProductImageEntityConverter;
import org.am.infrastructure.productimages.ImagesRepository;
import org.am.infrastructure.products.ProductRepository;
import org.am.library.entities.ImageEntity;
import org.am.library.entities.ProductEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductImageDAOImpl implements ProductImageDAO {

    private final ProductRepository productRepository;

    private final ImagesRepository imagesRepository;

    private final ProductImageEntityConverter productImageEntityConverter;

    private final ProductImageConverter productImageConverter;

    @Override
    @Transactional
    public ProductImage persist(ProductImage image) {

        final ProductEntity productEntity = productRepository.findBySid(image.getProductSid())
                .orElseThrow(() -> ProductNotFoundException.forSid(image.getProductSid()));

        final ImageEntity imageEntity = productImageEntityConverter.convert(image);

        imageEntity.setProduct(productEntity);

        final ImageEntity persisted = imagesRepository.save(imageEntity);
        return productImageConverter.convert(persisted);
    }

    @Override
    @Transactional
    public List<ProductImage> persist(List<ProductImage> images) {

        ProductImage image = images.get(0);
        final ProductEntity productEntity = productRepository.findBySid(image.getProductSid())
                .orElseThrow(() -> ProductNotFoundException.forSid(image.getProductSid()));
        final List<ImageEntity> imageEntities = getImageEntities(images, productEntity);

        return imagesRepository.saveAll(imageEntities).stream()
                .map(productImageConverter::convert)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ProductImage> findByProductSid(UUID productSid) {

        return imagesRepository.findByProductSid(productSid)
                .stream()
                .map(productImageConverter::convert)
                .collect(Collectors.toList());
    }

    private List<ImageEntity> getImageEntities(List<ProductImage> images, ProductEntity productEntity) {

        return images.stream().map(image -> {
            final ImageEntity imageEntity = productImageEntityConverter.convert(image);
            imageEntity.setProduct(productEntity);
            return imageEntity;
        }).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void delete(int imageId) {

        imagesRepository.deleteById(imageId);
    }
}
