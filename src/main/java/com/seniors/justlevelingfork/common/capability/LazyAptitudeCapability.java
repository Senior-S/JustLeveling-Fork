 package com.seniors.justlevelingfork.common.capability;
 
 import com.seniors.justlevelingfork.registry.RegistryCapabilities;
 import net.minecraft.core.Direction;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraftforge.common.capabilities.Capability;
 import net.minecraftforge.common.capabilities.ICapabilitySerializable;
 import net.minecraftforge.common.util.LazyOptional;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;
 
 public class LazyAptitudeCapability implements ICapabilitySerializable<CompoundTag> {
   private AptitudeCapability capability;
   private final LazyOptional<AptitudeCapability> optional;
   
   public LazyAptitudeCapability(AptitudeCapability provider) {
     this.capability = provider;
     this.optional = LazyOptional.of(this::createPlayerAbility);
   }
   
   private AptitudeCapability createPlayerAbility() {
     if (this.capability == null) this.capability = new AptitudeCapability(); 
     return this.capability;
   }
   
   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
     if (cap == RegistryCapabilities.APTITUDE) return this.optional.cast(); 
     return LazyOptional.empty();
   }
 
   
   public CompoundTag serializeNBT() {
     return createPlayerAbility().serializeNBT();
   }
 
   
   public void deserializeNBT(CompoundTag nbt) {
     createPlayerAbility().deserializeNBT(nbt);
   }
 }


